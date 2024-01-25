package com.dod.dodbackend.service.impl;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dod.dodbackend.model.Vacation;
import com.dod.dodbackend.repo.VacationRepo;
import com.dod.dodbackend.service.VacationService;

@Service
public class VacationServiceImpl implements VacationService {

    @Autowired
    private VacationRepo vacationRepo;

    private static final int MAX_SCRAPING_COUNT = 75;

    @Override
    public void fetchDataAndSaveVacation() throws IOException {

        int scrapingCount = 0;

        String url = "https://www.hotwire.com/packages";

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent(
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .header("Referer", "https://www.google.com/")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .get();

            Elements links = doc.select("div.uitk-card-content-section a.uitk-link");

            for (Element link : links) {
                String href = link.attr("href");
                String text = link.text();
                String vacationUrl = href;
                try {
                    Thread.sleep(5000);

                    Connection connection = Jsoup.connect(vacationUrl)
                            .userAgent(
                                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                            .header("Referer", "https://www.google.com/")
                            .header("Accept-Language", "en-US,en;q=0.9")
                            .timeout(15000) // Set a reasonable timeout value (in milliseconds)
                            .method(Connection.Method.GET);
                    Document vacationDoc = connection.execute().parse();

                    Elements deals = vacationDoc.select("div.uitk-card");
                    for (Element packageElement : deals) {

                        Vacation vacation = new Vacation();
                        vacation.setVacationPackage(text);

                        vacation.setName(packageElement.select("h3.uitk-heading-5").text());
                        vacation.setRatings(packageElement.select("div.uitk-rating span.is-visually-hidden").text());
                        vacation.setDetails(packageElement.select("div.uitk-text.truncate").text());
                        Element dealurl = packageElement.selectFirst("a[data-testid=packagecard-link-0]");
                        vacation.setDealUrl((dealurl != null) ? dealurl.absUrl("href") : "N/A");

                        if (!vacation.getName().isEmpty() && !vacation.getRatings().isEmpty()
                                && !vacation.getDetails().isEmpty() && dealurl != null) {

                            Element originalPriceElement = packageElement
                                    .selectFirst("del[data-testid=strike-through-price-trigger]");
                            String originalPriceStr = originalPriceElement != null ? originalPriceElement.text()
                                    : "N/A";
                            String formattedOriginalPrice = formatPrice(originalPriceStr);
                            vacation.setOriginalPrice(formattedOriginalPrice);

                            Element discountedPriceElement = packageElement
                                    .selectFirst("div[data-testid=price-with-trigger]");
                            String discountedPriceStr = discountedPriceElement != null ? discountedPriceElement.text()
                                    : "N/A";
                            String formattedDiscountedPrice = formatPrice(discountedPriceStr);
                            vacation.setDiscountPrice(formattedDiscountedPrice);

                            vacation.setDiscountPercentage(
                                    getDiscount(formattedOriginalPrice, formattedDiscountedPrice));
                            vacation.setImageUrl(packageElement.select("img.uitk-image-media").attr("src"));
                            vacation.setFromAndTo(packageElement.select("div.uitk-layout-flex-item").text());
                            vacationRepo.save(vacation);
                            scrapingCount++;

                            if (scrapingCount >= MAX_SCRAPING_COUNT) {
                                break;
                            }
                        }

                    }
                    if (scrapingCount >= MAX_SCRAPING_COUNT) {
                        break;
                    }
                } catch (SocketTimeoutException e) {
                    System.err.println("Timeout while connecting to: " + vacationUrl);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Vacation> getVacationDeals() {
        return this.vacationRepo.getVacationDeals();
    }

    private String formatPrice(String priceStr) {
        if (priceStr == null || priceStr.isEmpty()) {
            return "";
        }
        return priceStr.replaceAll("[$,]", "");
    }

    private Long getDiscount(String regularPriceStr, String salePriceStr) {
        if (regularPriceStr == null || regularPriceStr.isEmpty() || salePriceStr == null || salePriceStr.isEmpty()) {
            return 0L;
        }

        double regularPrice = Double.parseDouble(regularPriceStr);
        double salePrice = Double.parseDouble(salePriceStr);

        double discount = regularPrice - salePrice;
        double discountPercentage = (discount / regularPrice) * 100;

        return (long) discountPercentage;
    }

}
