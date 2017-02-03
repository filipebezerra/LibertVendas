package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class ChartGroupedData {

    private final String name;

    private final float amount;

    private ChartGroupedData(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }

    public static ChartGroupedData create(String name, float amount) {
        return new ChartGroupedData(name, amount);
    }

    public String getName() {
        return name;
    }

    public float getAmount() {
        return amount;
    }
}
