package com.example.telehealth.ui.dashboard

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.telehealth.AppConstants
import com.example.telehealth.databinding.FragmentDashboardBinding
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton
import com.zegocloud.uikit.service.defines.ZegoUIKitUser
import java.util.Collections

class AppointmentFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize Zego Call Service and setup buttons
        initializeCallService()

        return root
    }

    private fun initializeCallService() {
        val config = ZegoUIKitPrebuiltCallInvitationConfig()
        ZegoUIKitPrebuiltCallService.init(
            requireContext().applicationContext as Application?,
            AppConstants.appId,
            AppConstants.appSign,
            "Meet",
            "Meet",
            config
        )

        setupVoiceCall("Upul")
        setupVideoCall("Upul")
    }

    private fun setupVoiceCall(username: String) {
        val voiceCallButton: ZegoSendCallInvitationButton = binding.voiceCallButton
        voiceCallButton.setIsVideoCall(false)
        voiceCallButton.resourceID = "zego_uikit_call"
        voiceCallButton.setInvitees(Collections.singletonList(ZegoUIKitUser(username, username)))
    }

    private fun setupVideoCall(username: String) {
        val videoCallButton: ZegoSendCallInvitationButton = binding.videoCallButton
        videoCallButton.setIsVideoCall(true)
        videoCallButton.resourceID = "zego_uikit_call"
        videoCallButton.setInvitees(Collections.singletonList(ZegoUIKitUser(username, username)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
