package br.com.libertsolutions.libertvendas.app.data.importacao;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author Filipe Bezerra
 */
public class ImportacaoRepositories {
    private ImportacaoRepositories() {/* No instances */}

    private static ImportacaoRepository sImportacaoRepository;

    public static ImportacaoRepository getImportacaoRepository(@NonNull Context pContext) {
        if (sImportacaoRepository == null) {
            sImportacaoRepository = new SharedPreferencesImportacaoRepositoryImpl(pContext);
        }
        return sImportacaoRepository;
    }
}
