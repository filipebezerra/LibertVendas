package br.com.libertsolutions.libertvendas.app.domain.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class City {

    @Expose
    @SerializedName("idCidade")
    private Integer cityId;

    @Expose
    @SerializedName("codMunicipio")
    private String municipalityCode;

    @Expose
    @SerializedName("nome")
    private String name;

    @Expose
    @SerializedName("Estado")
    private State state;

    public Integer getCityId() {
        return cityId;
    }

    public City withCityId(final Integer cityId) {
        this.cityId = cityId;
        return this;
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }

    public City withMunicipalityCode(final String municipalityCode) {
        this.municipalityCode = municipalityCode;
        return this;
    }

    public String getName() {
        return name;
    }

    public City withName(final String name) {
        this.name = name;
        return this;
    }

    public State getState() {
        return state;
    }

    public City withState(final State state) {
        this.state = state;
        return this;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        City city = (City) o;

        return getCityId().equals(city.getCityId());
    }

    @Override public int hashCode() {
        return getCityId().hashCode();
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("City{");
        sb.append("cityId=").append(cityId);
        sb.append(", municipalityCode='").append(municipalityCode).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }
}
