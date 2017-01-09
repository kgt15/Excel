package mec.mec.excel16;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


/**
 * Created by jerin on 29/7/15.
 */
public  class myalert1 extends DialogFragment {


    private ButtonClickListener listener;

    public interface ButtonClickListener {
        void onClick(int option);
    }


    public void setButtonClickListener(ButtonClickListener listener) {
        this.listener = listener;
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//       // communicator=(Communicator)getActivity().getFragmentManager().findFragmentByTag("ABC");
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.alert, null);
        Button btn=(Button)view.findViewById(R.id.btn_yes);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              listener.onClick(0);




            }
        });

        Button btn2=(Button)view.findViewById(R.id.btn_no);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("evide","ethilla");
                dismiss();

            }
        });
        builder.setView(view);
        Dialog dialog=builder.create();
        return dialog;
    }
}

