package com.Project.ProjectUpgrade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Game {
    private int appId;
    private String name;
    private Date releaseDate;
    private int estimatedOwners;
    private int peakCCU;
    private int requiredAge;
    private double price;
    private int dlcCount;
    private String about;
    private String[] supportedLanguages;
    private String[] fullAudioLanguages;
    private String reviews;
    private String headerImage;
    private String website;
    private String supportUrl;
    private String supportEmail;
    private boolean windows;
    private boolean mac;
    private boolean linux;
    private int metacriticScore;
    private String metacriticUrl;
    private double userScore;
    private int positive;
    private int negative;
    private int scoreRank;
    private int achievements;
    private int recommendations;
    private String notes;
    private int averagePlaytimeForever;
    private int averagePlaytimeTwoWeeks;
    private int medianPlaytimeForever;
    private int medianPlaytimeTwoWeeks;
    private String developers;
    private String publishers;
    private String categories;
    private String genres;
    private String tags;
    private String screenshots;
    private String movies;

    private static final SimpleDateFormat[] dateFormats = {
        new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH),
        new SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
    };

    public Game(String[] values) throws ParseException {
        this.appId = parseInt(values[0]);
        this.name = values[1];
        this.releaseDate = parseDate(values[2]);
        this.estimatedOwners = parseInt(values[3].split(" - ")[0]);
        this.peakCCU = parseInt(values[4]);
        this.requiredAge = parseInt(values[5]);
        this.price = parseDouble(values[6]);
        this.dlcCount = parseInt(values[7]);
        this.about = values[8];
        this.supportedLanguages = parseArray(values[9]);
        this.fullAudioLanguages = parseArray(values[10]);
        this.reviews = values[11];
        this.headerImage = values[12];
        this.website = values[13];
        this.supportUrl = values[14];
        this.supportEmail = values[15];
        this.windows = parseBoolean(values[16]);
        this.mac = parseBoolean(values[17]);
        this.linux = parseBoolean(values[18]);
        this.metacriticScore = parseInt(values[19]);
        this.metacriticUrl = values[20];
        this.userScore = parseDouble(values[21]);
        this.positive = parseInt(values[22]);
        this.negative = parseInt(values[23]);
        this.scoreRank = parseInt(values[24]);
        this.achievements = parseInt(values[25]);
        this.recommendations = parseInt(values[26]);
        this.notes = values[27];
        this.averagePlaytimeForever = parseInt(values[28]);
        this.averagePlaytimeTwoWeeks = parseInt(values[29]);
        this.medianPlaytimeForever = parseInt(values[30]);
        this.medianPlaytimeTwoWeeks = parseInt(values[31]);
        this.developers = values[32];
        this.publishers = values[33];
        this.categories = values[34];
        this.genres = values[35];
        this.tags = values[36];
        this.screenshots = values[37];
        this.movies = values[38];
    }

    private int parseInt(String value) {
        return value.isEmpty() || value.equals("[]") ? 0 : Integer.parseInt(value);
    }

    private double parseDouble(String value) {
        return value.isEmpty() || value.equals("[]") ? 0.0 : Double.parseDouble(value);
    }

    private boolean parseBoolean(String value) {
        return !value.isEmpty() && Boolean.parseBoolean(value);
    }

    private String[] parseArray(String value) {
        if (value.isEmpty() || value.equals("[]")) {
            return new String[0];
        }
        return value.replace("[", "").replace("]", "").split(", ");
    }

    private Date parseDate(String value) throws ParseException {
        for (SimpleDateFormat format : dateFormats) {
            try {
                return format.parse(value);
            } catch (ParseException e) {
                // Continue trying other formats
            }
        }
        throw new ParseException("Unparseable date: " + value, 0);
    }

    public double getPrice() {
        return price;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getAchievements() {
        return achievements;
    }

    public String toCSV() {
        return appId + "," + name + "," + new SimpleDateFormat("dd/MM/yyyy").format(releaseDate) + "," + estimatedOwners + "," + peakCCU + "," + requiredAge + "," + price + "," + dlcCount + "," + about + ","
                + arrayToString(supportedLanguages) + "," + arrayToString(fullAudioLanguages) + "," + reviews + "," + headerImage + "," + website + "," + supportUrl + "," + supportEmail + ","
                + windows + "," + mac + "," + linux + "," + metacriticScore + "," + metacriticUrl + "," + userScore + "," + positive + "," + negative + "," + scoreRank + "," + achievements + ","
                + recommendations + "," + notes + "," + averagePlaytimeForever + "," + averagePlaytimeTwoWeeks + "," + medianPlaytimeForever + "," + medianPlaytimeTwoWeeks + "," + developers + ","
                + publishers + "," + categories + "," + genres + "," + tags + "," + screenshots + "," + movies;
    }

    private String arrayToString(String[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}







