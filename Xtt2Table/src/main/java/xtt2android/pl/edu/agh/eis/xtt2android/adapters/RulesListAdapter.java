package xtt2android.pl.edu.agh.eis.xtt2android.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.LinkedList;

import heart.alsvfd.Formulae;
import heart.xtt.Decision;
import heart.xtt.Rule;
import heart.xtt.Table;
import xtt2android.pl.edu.agh.eis.xtt2android.R;
import xtt2android.pl.edu.agh.eis.xtt2android.fragments.Xtt2TableFragment;
import xtt2android.pl.edu.agh.eis.xtt2android.listeners.CardClickListener;
import xtt2android.pl.edu.agh.eis.xtt2android.listeners.FormulaeValueClickListener;

public class RulesListAdapter extends RecyclerView.Adapter<RulesListAdapter.ViewHolder> {
    final private String KEYWORD_IF = "IF";
    final private String KEYWORD_THEN = "THEN";
    final private String KEYWORD_AND = "AND";
    final private String OPERATOR_SET = "SET";

    private Table mTable;
    private Rule[] mRules;
    private Xtt2TableFragment mXtt2Fragment;

    public RulesListAdapter(Table table, Xtt2TableFragment xtt2Fragment) {
        LinkedList<Rule> rules = table.getRules();
        mTable = table;
        mRules = table.getRules().toArray(new Rule[rules.size()]);
        mXtt2Fragment = xtt2Fragment;
    }

    @Override
    public RulesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_rule_card, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Rule rule = mRules[position];
        LinearLayout layout = (LinearLayout) holder.mCardView.findViewById(R.id.card_layout);
        Context context = layout.getContext();

        ((HorizontalScrollView) layout.getParent()).setOnTouchListener(new CardClickListener(mTable, rule, context));

        setUpRuleLayout(rule, layout, context);
    }

    private void setUpRuleLayout(Rule rule, LinearLayout layout, final Context context) {
        boolean isFirst = true;

        for (Formulae formulae : rule.getConditions()) {
            if (isFirst) {
                isFirst = false;
                layout.addView(createKeywordTextView(context, KEYWORD_IF));
            } else {
                layout.addView(createKeywordTextView(context, KEYWORD_AND));
            }

            layout.addView(createTextView(context, formulae.getAttribute().toString()));
            layout.addView(createOperatorTextView(context, formulae.getOp().toString()));

            TextView textView = createTextView(context, formulae.getValue().toString());
            textView.setOnClickListener(new FormulaeValueClickListener(context, formulae, mXtt2Fragment));

            layout.addView(textView);
        }

        isFirst = true;

        for (Decision decision : rule.getDecisions()) {
            if (isFirst) {
                isFirst = false;
                layout.addView(createKeywordTextView(context, KEYWORD_THEN));
            } else {
                layout.addView(createKeywordTextView(context, KEYWORD_AND));
            }

            layout.addView(createTextView(context, decision.getAttribute().toString()));
            layout.addView(createOperatorTextView(context, OPERATOR_SET));

            TextView textView = createTextView(context, decision.getDecision().toString());

            layout.addView(textView);
        }
    }

    private TextView createOperatorTextView(Context context, String text) {
        TextView view = createTextView(context, text);
        view.setTypeface(null, Typeface.BOLD_ITALIC);

        return view;
    }

    private TextView createKeywordTextView(Context context, String text) {
        TextView view = createTextView(context, text);
        view.setTypeface(null, Typeface.BOLD_ITALIC);
        view.setTextColor(Color.parseColor("#228f00"));

        return view;
    }

    private TextView createTextView(Context context, String text) {
        TextView view = new TextView(context);
        view.setText(text);
        view.setPadding(0, 0, 10, 0);

        return view;
    }

    @Override
    public int getItemCount() {
        return mRules.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;

        public ViewHolder(CardView v) {
            super(v);
            mCardView = v;
        }
    }
}
