package br.com.libertsolutions.libertvendas.app.presentation.addorder.orderitems;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderItem;
import br.com.libertsolutions.libertvendas.app.presentation.util.AndroidUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * @author Filipe Bezerra
 */
class SelectOrderItemsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.view_switcher) ViewSwitcher viewSwitcher;
    @BindView(R.id.input_layout_edit_quantity) TextInputLayout inputLayoutEditQuantity;
    @BindView(R.id.text_view_quantity) TextView textViewQuantity;
    @BindView(R.id.text_view_product_name) TextView textViewProductName;
    @BindView(R.id.text_view_product_price) TextView textViewProductPrice;
    @BindView(R.id.text_view_item_total) TextView textViewItemTotal;
    @BindView(R.id.text_view_product_code) TextView textViewProductCode;
    @BindView(R.id.text_view_product_barcode) TextView textViewProductBarcode;

    private final SelectOrderItemsCallbacks mItemsCallbacks;

    SelectOrderItemsViewHolder(
            final View itemView, @Nullable final SelectOrderItemsCallbacks itemsCallbacks) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mItemsCallbacks = itemsCallbacks;
    }

    @OnClick(R.id.text_view_quantity) public void onTextViewQuantityClicked() {
        viewSwitcher.showNext();
        viewSwitcher.getInAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(final Animation animation) {
                inputLayoutEditQuantity.getEditText().setText(textViewQuantity.getText());
            }

            @Override public void onAnimationEnd(final Animation animation) {
                AndroidUtils.focusThenShowKeyboard(itemView.getContext(),
                        inputLayoutEditQuantity.getEditText());
            }

            @Override public void onAnimationRepeat(final Animation animation) {}
        });
    }

    @OnEditorAction(R.id.edit_text_edit_quantity) public boolean onEditTextEditQuantityAction(
            KeyEvent key) {
        if (key == null || key.getAction() == KeyEvent.ACTION_UP) {
            final EditText editText = inputLayoutEditQuantity.getEditText();
            AndroidUtils.hideKeyboard(itemView.getContext(), editText);

            if (!TextUtils.isEmpty(editText.getText())) {
                Float quantity = Float.valueOf(editText.getText().toString());
                OrderItem orderItem = (OrderItem) itemView.getTag();
                mItemsCallbacks.onChangeOrderItemQuantityRequested(orderItem, quantity,
                        getAdapterPosition());
            }

            viewSwitcher.showPrevious();
        }
        return true;
    }

    @OnClick(R.id.button_add_item) public void onButtonAddItemClicked() {
        OrderItem orderItem = (OrderItem) itemView.getTag();
        mItemsCallbacks.onAddOrderItemRequested(orderItem, getAdapterPosition());
    }

    @OnClick(R.id.button_remove_item) public void onButtonRemoveItemClicked() {
        OrderItem orderItem = (OrderItem) itemView.getTag();
        mItemsCallbacks.onRemoveOrderItemRequested(orderItem, getAdapterPosition());
    }
}
