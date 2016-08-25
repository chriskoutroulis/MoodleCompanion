package ais.koutroulis.gr.ui;

/**
 * Created by c0nfr0ntier on 25/8/2016.
 */
public class AssignmentCard {

    private int epochTime;

    private String cardContent;

    public AssignmentCard(int epochTime, String cardContent) {
        this.epochTime = epochTime;
        this.cardContent = cardContent;
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
