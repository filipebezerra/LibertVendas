package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public enum TipoPessoa {

    PESSOA_FISICA("Pessoa Física", 0, 11), PESSOA_JURIDICA("Pessoa Jurídica", 1, 14);

    private final String mDescricao;

    private final int mIntType;

    private final int mCharCount;

    TipoPessoa(String pDescricao, int pIntType, int pCharCount) {
        mDescricao = pDescricao;
        mIntType = pIntType;
        mCharCount = pCharCount;
    }

    public String getDescricao() {
        return mDescricao;
    }

    public int getIntType() {
        return mIntType;
    }

    public int getCharCount() {
        return mCharCount;
    }

    @Override public String toString() {
        return getDescricao();
    }

    public boolean equals(int pIntType) {
        return mIntType == pIntType;
    }
}
