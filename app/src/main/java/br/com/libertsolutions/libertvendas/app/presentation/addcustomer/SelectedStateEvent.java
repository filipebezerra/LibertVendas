package br.com.libertsolutions.libertvendas.app.presentation.addcustomer;

import br.com.libertsolutions.libertvendas.app.domain.pojo.State;

/**
 * @author Filipe Bezerra
 */
public class SelectedStateEvent {

    private final State mState;

    private SelectedStateEvent(final State state) {
        mState = state;
    }

    static SelectedStateEvent newEvent(final State state) {
        return new SelectedStateEvent(state);
    }

    public State getState() {
        return mState;
    }
}
