package br.com.libertsolutions.libertvendas.app.presentation.citylist;

import br.com.libertsolutions.libertvendas.app.domain.pojo.City;

/**
 * @author Filipe Bezerra
 */
public class SelectedCityEvent {

    private final City mCity;

    private SelectedCityEvent(final City city) {
        mCity = city;
    }

    static SelectedCityEvent newEvent(final City city) {
        return new SelectedCityEvent(city);
    }

    public City getCity() {
        return mCity;
    }
}
