package droiddevelopers254.droidconke.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import droiddevelopers254.droidconke.R;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.M;

public class CollapsibleCard extends FrameLayout {
    private boolean mExpanded = false;
    private TextView mCardTitle;
    private HtmlTextView mCardDescription;
    private ImageView mExpandIcon;
    private View mTitleContainer;

    public CollapsibleCard(Context context) {
        this(context, null);
    }

    public CollapsibleCard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsibleCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.CollapsibleCard, 0, 0);
        final String cardTitle = arr.getString(R.styleable.CollapsibleCard_cardTitle);
        final String cardDescription = arr.getString(R.styleable.CollapsibleCard_cardDescription);
        arr.recycle();
        final View root = LayoutInflater.from(context)
                .inflate(R.layout.collapsible_card_content, this, true);

        mTitleContainer = root.findViewById(R.id.title_container);
        mCardTitle = root.findViewById(R.id.card_title);
        mCardTitle.setText(cardTitle);
        setTitleContentDescription(cardTitle);
        mCardDescription = root.findViewById(R.id.card_description);
        mCardDescription.setHtmlText(cardDescription);
        mExpandIcon = root.findViewById(R.id.expand_icon);
        if (SDK_INT < M) {
            mExpandIcon.setImageTintList(
                    AppCompatResources.getColorStateList(context, R.color.collapsing_section));
        }
        final Transition toggle = TransitionInflater.from(getContext())
                .inflateTransition(R.transition.info_card_toggle);
        final OnClickListener expandClick = new OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpanded = !mExpanded;
                toggle.setDuration(mExpanded ? 300L : 200L);
                TransitionManager.beginDelayedTransition((ViewGroup) root.getParent(), toggle);
                mCardDescription.setVisibility(mExpanded ? VISIBLE : GONE);
                mExpandIcon.setRotation(mExpanded ? 180f : 0f);
                // activated used to tint controls when expanded
                mExpandIcon.setActivated(mExpanded);
                mCardTitle.setActivated(mExpanded);
                setTitleContentDescription(cardTitle);
            }
        };
        mTitleContainer.setOnClickListener(expandClick);
    }
    public void setCardDescription(@NonNull String description) {
        mCardDescription.setHtmlText(description);
    }

    private void setTitleContentDescription(String cardTitle) {
        Resources res = getResources();
        mCardTitle.setContentDescription(cardTitle + ", " +
                (mExpanded ? res.getString(R.string.expanded) :
                        res.getString(R.string.collapsed)));
    }
}