package xtt2android.pl.edu.agh.eis.xtt2android.dialog;

import android.app.Dialog;
import android.content.Context;

import heart.alsvfd.Formulae;

abstract public class FormulaeDialog extends Dialog {

    Formulae mFormulae;

    FormulaeDialog(Context context, Formulae formulae) {
        super(context, 0);
        mFormulae = formulae;
    }
}
