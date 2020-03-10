package com.example.mommyhealthapp.Mommy.ui.Notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.Class.MommyHealthInfo;
import com.example.mommyhealthapp.Class.Notification;
import com.example.mommyhealthapp.Nurse.MommyRecordActivity;
import com.example.mommyhealthapp.Nurse.ui.SearchMother.SearchMotherFragment;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.RecyclerTouchListener;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    //private NotificationsViewModel notificationsViewModel;
    private RecyclerView recycleViewNotification;
    private ProgressBar progressBarNotification;
    private TextView textViewNoNotification;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference, nCollectionReference;
    private DocumentReference mDocumentReference;

    private SearchMotherFragment.SearchResultAdapter adapter;
    private List<Notification> notificationList;

    private String healthInfoId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // notificationsViewModel =
           //     ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        recycleViewNotification = (RecyclerView)root.findViewById(R.id.recycleViewNotification);
        progressBarNotification = (ProgressBar)root.findViewById(R.id.progressBarNotification);
        textViewNoNotification = (TextView)root.findViewById(R.id.textViewNoNotification);

        notificationList = new ArrayList<>();
        progressBarNotification.setVisibility(View.VISIBLE);
        textViewNoNotification.setVisibility(View.GONE);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mDocumentReference = mFirebaseFirestore.collection("Mommy").document(SaveSharedPreference.getID(getActivity()));
        mDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(final DocumentSnapshot documentSnapshot) {
                Mommy mommy = documentSnapshot.toObject(Mommy.class);
                healthInfoId = mommy.getHealthInfoId();
                mCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo");
                mCollectionReference.whereEqualTo("healthInfoId",healthInfoId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String key = "";
                        for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots)
                        {
                            key = documentSnapshots.getId();
                        }
                        nCollectionReference = mFirebaseFirestore.collection("MommyHealthInfo").document(key).collection("Notification");
                        nCollectionReference.orderBy("notificationDate", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(QueryDocumentSnapshot documentSnapshotss : queryDocumentSnapshots)
                                {
                                    Notification noti = documentSnapshotss.toObject(Notification.class);
                                    notificationList.add(noti);
                                }
                                Log.i("Testing", notificationList.size()+"");
                                NotificationRecordAdapter adapter = new NotificationRecordAdapter(notificationList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                recycleViewNotification.setLayoutManager(mLayoutManager);
                                recycleViewNotification.setItemAnimator(new DefaultItemAnimator());
                                recycleViewNotification.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                                recycleViewNotification.setAdapter(adapter);
                                progressBarNotification.setVisibility(View.GONE);
                                if (notificationList.isEmpty())
                                {
                                    textViewNoNotification.setVisibility(View.VISIBLE);
                                }
                                else{
                                    recycleViewNotification.setVisibility(View.VISIBLE);
                                    textViewNoNotification.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                });
            }
        });

        //final TextView textView = root.findViewById(R.id.text_notifications);
        //notificationsViewModel.getText().observe(this, new Observer<String>() {
        //    @Override
         //   public void onChanged(@Nullable String s) {
          //      textView.setText(s);
         //   }
        //});
        return root;
    }
    public class NotificationRecordAdapter extends RecyclerView.Adapter<NotificationRecordAdapter.MyViewHolder>{
        private List<Notification> listP;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View resultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_record_view, parent, false);
            return new MyViewHolder(resultView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Notification noti = listP.get(position);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            holder.notidetail.setText(noti.getNotificationDetail());
            Log.i("Testing1", noti.getNotificationDetail());
            Log.i("Testing1", noti.getNotificationDate()+"");
            holder.notidate.setText(dateFormat.format(noti.getNotificationDate()));
        }

        @Override
        public int getItemCount() {
            return listP.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView notidetail, notidate;

            public MyViewHolder(View view)
            {
                super(view);
                notidetail = (TextView)view.findViewById(R.id.notidetail);
                notidate = (TextView)view.findViewById(R.id.notidate);
            }
        }

        public NotificationRecordAdapter(List<Notification> list)
        {
            listP = list;
        }
    }
}