package com.jenzz.materialpreference;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.text.TextUtils.isEmpty;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.jenzz.materialpreference.Typefaces.getRobotoRegular;

import com.alexcruz.papuhwalls.R;

public class Preference extends android.preference.Preference {

  TextView titleView;
  TextView summaryView;

  public Preference(Context context) {
    super(context);
    init(context, null, 0, 0);
  }

  public Preference(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0, 0);
  }

  public Preference(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr, 0);
  }

  @TargetApi(LOLLIPOP)
  public Preference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs, defStyleAttr, defStyleRes);
  }

  private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    TypedArray typedArray =
        context.obtainStyledAttributes(attrs, new int[] { android.R.attr.icon }, defStyleAttr,
            defStyleRes);
    typedArray.recycle();
  }

  @Override
  protected View onCreateView(ViewGroup parent) {
    LayoutInflater layoutInflater =
        (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
    View layout = layoutInflater.inflate(R.layout.mp_preference, parent, false);

    ViewGroup widgetFrame = (ViewGroup) layout.findViewById(R.id.widget_frame);
    int widgetLayoutResId = getWidgetLayoutResource();
    if (widgetLayoutResId != 0) {
      layoutInflater.inflate(widgetLayoutResId, widgetFrame);
    }
    widgetFrame.setVisibility(widgetLayoutResId != 0 ? VISIBLE : GONE);

    return layout;
  }

  @Override
  protected void onBindView(View view) {
    super.onBindView(view);

    CharSequence title = getTitle();
    titleView = (TextView) view.findViewById(R.id.title);
    titleView.setText(title);
    titleView.setVisibility(!isEmpty(title) ? VISIBLE : GONE);
    titleView.setTypeface(getRobotoRegular(getContext()));

    CharSequence summary = getSummary();
    summaryView = (TextView) view.findViewById(R.id.summary);
    summaryView.setText(summary);
    summaryView.setVisibility(!isEmpty(summary) ? VISIBLE : GONE);
    summaryView.setTypeface(getRobotoRegular(getContext()));
  }
}
