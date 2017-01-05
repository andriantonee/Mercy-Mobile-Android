package mercy.tourney.View.ManageTeam;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mercy.tourney.Model.Basic.SearchMember;
import mercy.tourney.Presenter.ManageTeam.InviteMemberPresenter;
import mercy.tourney.R;

/**
 * Created by Andrianto on 12/12/2016.
 */
public class SearchMemberAdapter extends RecyclerView.Adapter<SearchMemberViewHolder> {
    private List<SearchMember> memberList;
    private InviteMemberPresenter instanceManageMemberPresenter;
    private Activity activity;
    private Boolean FLAG_INSTANT_REMOVE = false;

    public SearchMemberAdapter(List<SearchMember> memberList, InviteMemberPresenter instanceManageMemberPresenter, Activity activity, Boolean FLAG_INSTANT_REMOVE) {
        this.memberList = memberList;
        this.instanceManageMemberPresenter = instanceManageMemberPresenter;
        this.activity = activity;
        this.FLAG_INSTANT_REMOVE = FLAG_INSTANT_REMOVE;
    }

    @Override
    public int getItemCount() {
        if (memberList != null) {
            return memberList.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(final SearchMemberViewHolder holder, int position) {
        final SearchMember member = memberList.get(position);

        holder.name_text_view.setText(member.getName());
        holder.username_text_view.setText(member.getUsername());
        if (member.getInvited()) {
            Date date = new Date(Long.parseLong(String.valueOf(member.getInvited_at())) * 1000);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
            holder.invited_at_text_view.setText(simpleDateFormat.format(date));
            holder.add_member_image_view.setVisibility(View.GONE);
            holder.invited_at_text_view.setVisibility(View.VISIBLE);
            holder.remove_member_image_view.setVisibility(View.VISIBLE);
        } else {
            holder.invited_at_text_view.setVisibility(View.GONE);
            holder.remove_member_image_view.setVisibility(View.GONE);
            holder.add_member_image_view.setVisibility(View.VISIBLE);
        }

        final int index = position;

        holder.add_member_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instanceManageMemberPresenter.invite_member(((ManageMemberActivity) activity).get_token_type(), ((ManageMemberActivity) activity).get_access_token(), member.getUsername());

                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                holder.invited_at_text_view.setText(simpleDateFormat.format(date));

                holder.add_member_image_view.setVisibility(View.GONE);
                holder.remove_member_image_view.setVisibility(View.VISIBLE);
                holder.invited_at_text_view.setVisibility(View.VISIBLE);
            }
        });

        holder.remove_member_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instanceManageMemberPresenter.cancel_invite_member(((ManageMemberActivity) activity).get_token_type(), ((ManageMemberActivity) activity).get_access_token(), member.getUsername());

                if (FLAG_INSTANT_REMOVE) {
                    memberList.remove(index);
                    notifyItemRemoved(index);
                    notifyItemRangeChanged(0, memberList.size());
                } else {
                    holder.remove_member_image_view.setVisibility(View.GONE);
                    holder.invited_at_text_view.setVisibility(View.GONE);
                    holder.add_member_image_view.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public SearchMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View _view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.search_member_card_view, parent, false);

        return new SearchMemberViewHolder(_view);
    }
}
