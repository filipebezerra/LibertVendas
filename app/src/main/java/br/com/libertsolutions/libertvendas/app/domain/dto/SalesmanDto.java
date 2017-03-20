package br.com.libertsolutions.libertvendas.app.domain.dto;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Salesman;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Filipe Bezerra
 */
public class SalesmanDto {

    @Expose @SerializedName("error") public boolean error;

    @Expose @SerializedName("mensagem") public String message;

    @Expose @SerializedName("Vendedor") public Salesman salesman;
}
