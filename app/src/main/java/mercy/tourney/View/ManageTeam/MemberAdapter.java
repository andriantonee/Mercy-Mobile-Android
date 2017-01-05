package mercy.tourney.View.ManageTeam;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mercy.tourney.Model.Basic.Member;
import mercy.tourney.R;

/**
 * Created by Andrianto on 12/8/2016.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberViewHolder> {
    private List<Member> memberList;

    public MemberAdapter(List<Member> memberList) {
        this.memberList = memberList;
    }

    @Override
    public int getItemCount() {
        if (memberList != null){
            return memberList.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        Member member = memberList.get(position);

        holder.name_text_view.setText(member.getName());
        holder.username_text_view.setText(member.getUsername());
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View _view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.member_card_view, parent, false);

        return new MemberViewHolder(_view);
    }
}
