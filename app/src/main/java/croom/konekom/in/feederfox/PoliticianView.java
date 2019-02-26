package croom.konekom.in.feederfox;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.*;
import com.github.rtoshiro.view.video.FullscreenVideoLayout;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;
import croom.konekom.in.feederfox.adapter.CommentAdapter;
import croom.konekom.in.feederfox.constant.Config;
import croom.konekom.in.feederfox.model.Politician;

import java.io.IOException;

public class PoliticianView extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener{
    private TextView politicianName,educationQualification,familyHistory;
    FullscreenVideoLayout videoLayout;
    private ImageView politicianImage;
    private static Politician politician;
    private CommentAdapter commentAdapter;
    private ListView commentList;
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private YouTubePlayerView youTubeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.politician_view);
        politicianName = findViewById(R.id.politicianName);
        politicianImage = findViewById(R.id.politicianImage);
        educationQualification = findViewById(R.id.qualification);
        familyHistory = findViewById(R.id.familyHistory);
        commentList = findViewById(R.id.commentsList);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.DEVELOPER_KEY, this);
        educationQualification.setText(politician.getQualification());
        politicianName.setText(politician.getName());
        familyHistory.setText(politician.getFamilyhistory());
        Picasso.get().load(politician.getImage_URL()).into(politicianImage);
        commentAdapter= new CommentAdapter(politician.getPoliticalComment(),PoliticianView.this);
        commentList.setAdapter(commentAdapter);
        int length=politician.getImage_URL().length();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            youTubePlayer.loadVideo(politician.getVideoUrl().substring(30));

            // Hiding player controls
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }
    }

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == RECOVERY_DIALOG_REQUEST) {
        // Retry initialization if user performed a recovery action
        getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY, this);
    }
}

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
           Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }
    }

    public static void setPolitician(Politician politician){
        PoliticianView.politician = politician;
    }
}
