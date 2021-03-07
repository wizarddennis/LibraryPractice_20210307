package kr.co.tjoeun.librarypractice_20210307

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        // 전화걸기 버튼 눌리면 => 권한이 있는지 확인하고 => 실제 전화 걸어보자.
        callBtn.setOnClickListener {
//            1. 권한이 있는지/거절됬는지에 따른 [행동 방침](Interface) 변수 생성
//            권한이 있으면 => Uri / ACTION_CALL 등등 내용 실행
//            권한이 없으면 => 토스트로 연결 불가 안내.
            val pl = object : PermissionListener {
                override fun onPermissionGranted() {
                    val myUri = Uri.parse("tel: 010-2222-3333")
                    val myIntent = Intent(Intent.ACTION_CALL, myUri)
                    startActivity(myIntent)
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(mContext, "권한이 없어서 전화 연결불가.", Toast.LENGTH_SHORT).show()
                }
            }


//            2. 1에서 만든 [행동 방침]을 가지고 => 실제 권한 확인.
            TedPermission.with(mContext)
                .setPermissionListener(pl)
                .setDeniedMessage("권한을 거부하면 연결불가합니다. [설정] > [권한]에서 셋팅해주세요.")
                .setPermissions(android.Manifest.permission.CALL_PHONE)
                .check()
        }

        // 이미지뷰를 눌렀을때? => 사진 보기 화면 이동.
        profileImg.setOnClickListener {
            val myIntent = Intent(mContext, ViewPhotoActivity::class.java)
            startActivity(myIntent)
        }

    }

    override fun setValues() {
//      인터넷의 이미지를 recentImg에 적용
        Glide.with(mContext).load("https://i.pinimg.com/originals/50/f9/e4/50f9e45b88c67b268aeaabb178bc9843.png").into(recentImg)

    }


}