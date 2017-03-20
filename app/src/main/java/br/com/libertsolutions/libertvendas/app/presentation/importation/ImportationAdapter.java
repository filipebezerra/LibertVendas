package br.com.libertsolutions.libertvendas.app.presentation.importation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Company;
import java.util.List;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.presentation.PresentationInjector.provideEventBus;

/**
 * @author Filipe Bezerra
 */
class ImportationAdapter extends RecyclerView.Adapter<ImportationViewHolder> {

    private final List<Company> mCompanies;

    private final int [] mProgressValues;

    ImportationAdapter(final List<Company> companies) {
        mCompanies = companies;
        mProgressValues = new int[companies.size()];
    }

    @Override public ImportationViewHolder onCreateViewHolder(
            final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_importation, parent, false);
        return new ImportationViewHolder(itemView);
    }

    @Override public void onBindViewHolder(final ImportationViewHolder holder, final int position) {
        holder.textViewCompanyName.setText(mCompanies.get(position).getName());
        holder.progressButton.setIndeterminateProgressMode(true);

        final int progress = mProgressValues[position];

        Timber.d("setProgress of %d to %d", position, progress);
        holder.progressButton.setProgress(progress);
        holder.buttonRetry.setVisibility(progress == -1 ? View.VISIBLE : View.GONE);

        holder.buttonRetry.setOnClickListener(v -> {
            Company company = mCompanies.get(holder.getAdapterPosition());
            provideEventBus().post(RetryImportEvent.newEvent(company));
        });
    }

    @Override public int getItemCount() {
        return mCompanies.size();
    }

    void updateProgress(Company company, int progress) {
        int indexOf = mCompanies.indexOf(company);
        Timber.d("updateProgress of %d to %d", indexOf, progress);
        mProgressValues[indexOf] = progress;
        notifyItemChanged(indexOf);
    }
}
