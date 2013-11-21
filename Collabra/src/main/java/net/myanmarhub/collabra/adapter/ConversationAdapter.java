package net.myanmarhub.collabra.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.appspot.myanhub_collabra.collabra.model.Conversation;
import com.appspot.myanhub_collabra.collabra.model.User;

import net.myanmarhub.collabra.R;
import net.myanmarhub.collabra.dao.ConversationDAO;
import net.myanmarhub.collabra.dao.UserDAO;
import net.myanmarhub.collabra.util.Utils;
import net.myanmarhub.collabra.widget.ETextView;

/**
 * Tin Htoo Aung (Myanmar Hub) on 21/11/13.
 */
public class ConversationAdapter extends CursorAdapter {

    private ConversationDAO conversationDAO;
    private UserDAO userDAO;
    private Context mContext;

    public ConversationAdapter(Context context, Cursor c) {
        super(context, c, true);
        this.conversationDAO = new ConversationDAO(context);
        this.userDAO = new UserDAO(context);
        this.mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_conversation, null);
        view.setTag(R.id.item_conversation_lblMessage, view.findViewById(R.id.item_conversation_lblMessage));
        view.setTag(R.id.item_conversation_lblName, view.findViewById(R.id.item_conversation_lblName));
        view.setTag(R.id.item_conversation_lblTimestamp, view.findViewById(R.id.item_conversation_lblTimestamp));
        view.setTag(R.id.item_conversation_rlbackground, view.findViewById(R.id.item_conversation_rlbackground));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ETextView lblName = (ETextView) view.getTag(R.id.item_conversation_lblName);
        ETextView lblMessage = (ETextView) view.getTag(R.id.item_conversation_lblMessage);
        ETextView lblTimestamp = (ETextView) view.getTag(R.id.item_conversation_lblTimestamp);
        RelativeLayout rlBackground = (RelativeLayout) view.getTag(R.id.item_conversation_rlbackground);

        Conversation conversation = conversationDAO.toObject(cursor);
        User sender = userDAO.toObject(userDAO.getById(conversation.getSender().getId()));
        if (sender.getUsername().equals(Utils.getSettings(mContext, "username"))) {
            lblName.setVisibility(View.GONE);
            rlBackground.setBackgroundResource(R.drawable.bubble_old_green_left);
        } else {
            lblName.setText(sender.getUsername());
            rlBackground.setBackgroundResource(R.drawable.bubble_old_turquoise_right);
        }

        lblMessage.setText(conversation.getMessage());
        lblTimestamp.setText(conversation.getSendAt().toString());
    }
}
