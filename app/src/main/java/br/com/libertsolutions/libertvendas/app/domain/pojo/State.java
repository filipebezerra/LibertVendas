package br.com.libertsolutions.libertvendas.app.domain.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class State {

    @Expose
    @SerializedName("idEstado")
    private Integer stateId;

    @Expose
    @SerializedName("uf")
    private String federativeUnit;

    @Expose
    @SerializedName("nome")
    private String name;

    public Integer getStateId() {
        return stateId;
    }

    public State withStateId(final Integer stateId) {
        this.stateId = stateId;
        return this;
    }

    public String getFederativeUnit() {
        return federativeUnit;
    }

    public State withFederativeUnit(final String federativeUnit) {
        this.federativeUnit = federativeUnit;
        return this;
    }

    public String getName() {
        return name;
    }

    public State withName(final String name) {
        this.name = name;
        return this;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        State state = (State) o;

        return getStateId().equals(state.getStateId());
    }

    @Override public int hashCode() {
        return getStateId().hashCode();
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("State{");
        sb.append("stateId=").append(stateId);
        sb.append(", federativeUnit='").append(federativeUnit).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
