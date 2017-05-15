package br.com.libertsolutions.libertvendas.app.presentation.customerlist;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFilter;
import java.util.List;

import static android.support.v7.widget.RecyclerView.NO_POSITION;
import static android.text.TextUtils.isEmpty;
import static android.view.LayoutInflater.from;
import static br.com.libertsolutions.libertvendas.app.R.layout.list_item_customer;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatCpforCnpj;
import static br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils.formatPhoneNumber;

/**
 * @author Filipe Bezerra
 */
public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListViewHolder>
        implements Filterable {

    private final List<Customer> mCustomers;

    private List<Customer> mCustomersOriginalCopy;

    private CustomerListFilter mFilter;

    CustomerListAdapter(final List<Customer> customers) {
        mCustomers = customers;
    }

    @Override public CustomerListViewHolder onCreateViewHolder(
            final ViewGroup parent, final int viewType) {
        final View itemView = from(parent.getContext()).inflate(list_item_customer, parent, false);
        return new CustomerListViewHolder(itemView);
    }

    @Override public void onBindViewHolder(
            final CustomerListViewHolder holder, final int position) {
        final Customer customer = mCustomers.get(position);
        final Context context = holder.itemView.getContext();

        holder.textViewSocialName.setText(customer.getName());
        holder.textViewCpfOrCnpj.setText(formatCpforCnpj(customer.getCpfOrCnpj()));

        if (!isEmpty(customer.getMainPhone()) && !isEmpty(customer.getSecondaryPhone())) {
            holder.textViewPhone.setText(
                    context.getString(R.string.customer_list_template_text_phones,
                            formatPhoneNumber(customer.getMainPhone()),
                            formatPhoneNumber(customer.getSecondaryPhone())));
        } else if (!isEmpty(customer.getMainPhone())) {
            holder.textViewPhone.setText(formatPhoneNumber(customer.getMainPhone()));
        } else if (!isEmpty(customer.getSecondaryPhone())) {
            holder.textViewPhone.setText(formatPhoneNumber(customer.getSecondaryPhone()));
        } else {
            holder.textViewPhone.setText(context.getString(R.string.customer_list_text_no_phone));
        }

        if (!isEmpty(customer.getEmail())) {
            holder.textViewEmail.setText(customer.getEmail());
        } else {
            holder.textViewEmail.setText(context.getString(R.string.customer_list_text_no_email));
        }

        if (!isEmpty(customer.getFantasyName())) {
            holder.textViewFantasyName.setText(customer.getFantasyName());
        } else {
            holder.textViewFantasyName
                    .setText(context.getString(R.string.customer_list_text_no_fantasy_name));
        }

        if (!isEmpty(customer.getCode())) {
            holder.textViewCustomerCode
                    .setText(context.getString(R.string.customer_list_template_customer_code,
                            customer.getCode()));
        } else {
            holder.textViewCustomerCode
                    .setText(context.getString(R.string.customer_list_text_no_customer_code));
        }
    }

    @Override public int getItemCount() {
        return mCustomers.size();
    }

    @Override public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new CustomerListFilter();
        }
        return mFilter;
    }

    public boolean isEmptyList() {
        return getItemCount() == 0;
    }

    public Customer getCustomer(@IntRange(from = 0) int position) {
        if (position < 0 || position >= mCustomers.size()) {
            return null;
        }
        return mCustomers.get(position);
    }

    public int updateCustomer(final Customer customer) {
        if (customer != null) {
            final int currentPosition = mCustomers.indexOf(customer);
            if (currentPosition == NO_POSITION) {
                int lastPosition = mCustomers.size();
                mCustomers.add(lastPosition, customer);
                notifyItemInserted(lastPosition);
                return lastPosition;
            } else {
                mCustomers.set(currentPosition, customer);
                notifyItemChanged(currentPosition);
                return currentPosition;
            }
        }
        return NO_POSITION;
    }

    public int getItemPosition(@NonNull Customer customer) {
        return mCustomers.indexOf(customer);
    }

    private class CustomerListFilter extends BaseFilter<Customer> {

        CustomerListFilter() {
            super(CustomerListAdapter.this, mCustomers, mCustomersOriginalCopy);
        }

        @Override protected String[] filterValues(final Customer customer) {
            return new String[] { customer.getName(), customer.getContact(),
                    customer.getCpfOrCnpj(), customer.getCode()};
        }
    }
}
