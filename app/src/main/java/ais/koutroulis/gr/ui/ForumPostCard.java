package ais.koutroulis.gr.ui;

/**
 * Created by c0nfr0ntier on 25/8/2016.
 */
public class ForumPostCard {

    private int epochTime;

    private String cardContent;

    public ForumPostCard(int epochTime, String cardContent) {
        this.cardContent = cardContent;
        this.epochTime = epochTime;
    }

    public String getCardContent() {
        return cardContent;
    }

    public void setCardContent(String cardContent) {
        this.cardContent = cardContent;
    }

    public int getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(int epochTime) {
        this.epochTime = epochTime;
    }
}
