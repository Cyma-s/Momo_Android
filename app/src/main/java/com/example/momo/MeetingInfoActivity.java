package com.example.momo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.TextTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

import java.util.HashMap;
import java.util.Map;

public class MeetingInfoActivity extends AppCompatActivity {
    private static final String TAG = "kakaolink";
    // 모임 전체 정보창 Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_info);

        Button addPeople = findViewById(R.id.personaddbutton);

        addPeople.setOnClickListener(new View.OnClickListener() {
            // 친구 초대하는 버튼
            @Override
            public void onClick(View view) {
                sendKakaoTemplate();
            }
        });
    }

    public void sendKakaoTemplate(){
        String content_text = "모모에서 모임에 참여해보세요!";
        String meetingId="asdf";
        String parameter = "message="+meetingId;
        TextTemplate params = TextTemplate.newBuilder(
                content_text,
                LinkObject.newBuilder()
                .setWebUrl("https://developers.kakao.com")
                .setMobileWebUrl("https://developes.kakao.com")
                .build())

                .addButton(new ButtonObject("모임에 참여하기", LinkObject.newBuilder()
                        .setAndroidExecutionParams(parameter)
                        .build()))
                .build();
        Map<String, String> serverCallbackArgs = new HashMap<>();
        serverCallbackArgs.put("meeting_id", "${current_user_id}");

        KakaoLinkService.getInstance().sendDefault(this, params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e(errorResult.toString());
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {

            }
        });
    }

    public void share_kakao_template(){
        // 템플릿 종류 :  피트, 리스크, 커머스
        String templateId = "58666"; // 템플릿 피드아이디

        // 템플릿에서  사용할 데이터 맵
        Map<String, String> templateArgs = new HashMap<String, String>();
        templateArgs.put("title","제목");
        templateArgs.put("sub","텍스트");
        templateArgs.put("button", "모임에 참여하기");

        KakaoLinkService.getInstance().sendCustom(this, templateId, templateArgs, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("EOTEST",errorResult.toString());
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다.

            }
        });
    }

}