package br.com.libertsolutions.libertvendas.app.presentation.citylist;

import android.support.annotation.IntRange;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.City;
import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;

/**
 * @author Filipe Bezerra
 */
class CityListAdapter extends RecyclerView.Adapter<CityListViewHolder> implements Filterable {

    private final List<City> mCities;

    private List<City> mCitiesOriginalCopy;

    private CityListFilter mCityListFilter;

    CityListAdapter(final List<City> cities) {
        mCities = cities;
    }

    @Override public CityListViewHolder onCreateViewHolder(
            final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_city, parent, false);
        return new CityListViewHolder(itemView);
    }

    @Override public void onBindViewHolder(final CityListViewHolder holder, final int position) {
        holder.textViewName.setText(mCities.get(position).getName());
    }

    @Override public int getItemCount() {
        return mCities.size();
    }

    @Override public Filter getFilter() {
        if (mCityListFilter == null) {
            mCityListFilter = new CityListFilter();
        }
        return mCityListFilter;
    }

    City getCity(@IntRange(from = 0) int position) {
        if (position >= 0 && position < mCities.size()) {
            return mCities.get(position);
        }
        return null;
    }

    private class CityListFilter extends Filter {
        @Override protected FilterResults performFiltering(final CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mCitiesOriginalCopy == null) {
                mCitiesOriginalCopy = new ArrayList<>(mCities);
            }

            if (isEmpty(prefix)) {
                List<City> list = new ArrayList<>(mCitiesOriginalCopy);
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                List<City> values = new ArrayList<>(mCitiesOriginalCopy);
                final List<City> newValues = new ArrayList<>();

                for(City city : values) {
                    if (city.getName().trim().toLowerCase().contains(prefixString)) {
                        newValues.add(city);
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override protected void publishResults(
                final CharSequence prefix, final FilterResults results) {
            mCities.clear();
            //noinspection unchecked
            mCities.addAll((List<City>) results.values);
            notifyDataSetChanged();
        }
    }
}
