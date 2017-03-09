package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zeeice.lagdev.R;

import java.util.ArrayList;

import Model.UserObject;

/**
 * Created by Oriaje on 3/6/2017.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private ArrayList<UserObject>results;
    private Context mContext;


    public SearchResultAdapter(Context mContext)
    {
        this.mContext = mContext;
        this.results = new ArrayList<>();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.search_result_row,
                parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        UserObject userObject = results.get(position);

        Picasso.with(mContext)
                .load(userObject.getImageUrl())
                .placeholder(R.color.colorAccent)
                .into(holder.imageView);

        holder.usernameView.setText(userObject.getUsername());
    }

    @Override
    public int getItemCount()
    {
        return results.size();
    }

    public void setResultLists(ArrayList<UserObject> results)
    {
        this.results.clear();
        this.results.addAll(results);

        //notify tha adapter to refresh the list
        notifyDataSetChanged();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder
{
    public TextView usernameView;
    public ImageView imageView;

    public MyViewHolder(View itemView) {
        super(itemView);

        usernameView = (TextView)itemView.findViewById(R.id.devName);
        imageView = (ImageView)itemView.findViewById(R.id.userImage);
    }
}
