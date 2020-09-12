package de.tobiasbielefeld.searchbar.ui;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.tobiasbielefeld.searchbar.R;

/**
 * A list item that's made of a main text and a button off to the side.
 *
 * This class is used for both the suggestions and history line items. (Well,
 * just the suggestions at the moment, but it ought to be used for history
 * too.)
 *
 * Provides methods for adding listeners for clicking the list item, long
 *
 * clicking the list item, or clicking the button.
 * This class is pretty tied to the list_item.xml file. I guess you
 * could create other layouts, but thisview will need to have at least two
 * children: one with an id of "text" and one with an id of "button"
 *
 */
public class ListItem extends LinearLayout {

	/**
	 * Constructor. I think this is the one used by the xml inflater
	 */
	public ListItem(Context c, AttributeSet a) {
		super(c, a);
	}

	public ListItem(Context c, AttributeSet a, int i, int j) {
		super(c, a, i, j);
	}

	/**
	 * The given function will be called when the main item is clicked.
	 */
	public void setItemClickListener(ClickListener listener) {
		TextView textView = (TextView) findViewById(R.id.text);
		textView.setOnClickListener(v ->
			listener.onClick(textView.getText().toString())
		);
	}

	/**
	 * The given function will be called when the main item is long clicked.
	 */
	public void setItemLongClickListener(ClickListener listener) {
		TextView textView = (TextView) findViewById(R.id.text);
		textView.setOnLongClickListener(v -> {
				listener.onClick(textView.getText().toString());
				return true;
			}
		);
	}

	/**
	 * The given function will be called when the button is clicked.
	 */
	public void setButtonClickListener(ClickListener listener) {
		TextView textView = (TextView) findViewById(R.id.text);
		View button = findViewById(R.id.button);

		button.setOnClickListener(v ->
			listener.onClick(textView.getText().toString())
		);
	}

	/**
	 * The given function will be called when the button is long clicked.
	 */
	public void setButtonLongClickListener(ClickListener listener) {
		TextView textView = (TextView) findViewById(R.id.text);
		View button = findViewById(R.id.button);

		button.setOnLongClickListener(v -> {
				listener.onClick(textView.getText().toString());
				return true;
			}
		);
	}

	/**
	 * An interface for reacting to clicks.
	 *
	 * Modeled after android.view.View.OnClickListener, but takes a String instead.
	 */
	public interface ClickListener {
		public void onClick(String s);
	}

	/**
	 * This class turns helps turn the list of objects into the UI list
	 */
	public static class ListItemAdapter<T> extends ArrayAdapter {

		private ClickListener itemClickListener;
		private ClickListener itemLongClickListener;
		private ClickListener buttonClickListener;
		private ClickListener buttonLongClickListener;

		public ListItemAdapter(Context context, T[] objects) {
			super(context, R.layout.list_item, R.id.text, objects);
		}

		public ListItemAdapter(Context context, List<T> objects) {
			super(context, R.layout.list_item, R.id.text, objects);
		}

		/**
		 * The given click listener will be applied to each item in the list
		 */
		public void setItemClickListener(ClickListener listener) {
			itemClickListener = listener;
		}

		/**
		 * The given click listener will be applied to each item in the list
		 */
		public void setItemLongClickListener(ClickListener listener) {
			itemLongClickListener = listener;
		}

		/**
		 * The given click listener will be applied to each item in the list
		 */
		public void setButtonClickListener(ClickListener listener) {
			buttonClickListener = listener;
		}

		/**
		 * The given click listener will be applied to each item in the list
		 */
		public void setButtonLongClickListener(ClickListener listener) {
			buttonLongClickListener = listener;
		}

		@Override
		public View getView (int position, View convertView, ViewGroup parent) {
			ListItem v = (ListItem) super.getView(position, convertView, parent);
			// apply the listeners to each view
			if (itemClickListener != null)
				v.setItemClickListener(itemClickListener);
			if (itemLongClickListener != null)
				v.setItemLongClickListener(itemLongClickListener);
			if (buttonClickListener != null)
				v.setButtonClickListener(buttonClickListener);
			if (buttonLongClickListener != null)
				v.setButtonLongClickListener(buttonLongClickListener);
			return v;
		}
	}
}
