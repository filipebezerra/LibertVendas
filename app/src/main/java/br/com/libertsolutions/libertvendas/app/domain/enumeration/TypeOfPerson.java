package br.com.libertsolutions.libertvendas.app.domain.enumeration;

/**
 * @author Filipe Bezerra
 */
public enum TypeOfPerson {

    PRIVATE_INDIVIDUAL("Pessoa Física", 0, 11), LEGAL_ENTITY("Pessoa Jurídica", 1, 14);

    private final String mDescription;

    private final int mOrdinalType;

    private final int mCharCount;

    TypeOfPerson(final String description, final int ordinalType, final int charCount) {
        mDescription = description;
        mOrdinalType = ordinalType;
        mCharCount = charCount;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getOrdinalType() {
        return mOrdinalType;
    }

    public int getCharCount() {
        return mCharCount;
    }

    @Override public String toString() {
        return getDescription();
    }

    public static TypeOfPerson valueOf(int ordinalType) {
        for (TypeOfPerson typeOfPerson : values()) {
            if (typeOfPerson.getOrdinalType() == ordinalType) {
                return typeOfPerson;
            }
        }
        return null;
    }
}
