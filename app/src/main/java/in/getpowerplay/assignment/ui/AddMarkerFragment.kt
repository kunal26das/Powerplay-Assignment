package `in`.getpowerplay.assignment.ui

import `in`.getpowerplay.assignment.R
import `in`.getpowerplay.assignment.databinding.FragmentAddMarkerBinding
import `in`.getpowerplay.assignment.mvvm.MarkerViewModel
import `in`.getpowerplay.assignment.source.model.Drawing
import `in`.getpowerplay.assignment.source.model.Marker
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_add_marker.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class AddMarkerFragment : BottomSheetDialogFragment() {

    private var isEditable = false
    private lateinit var marker: Marker
    private lateinit var drawing: Drawing
    private lateinit var binding: FragmentAddMarkerBinding
    private val viewModel by sharedViewModel<MarkerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
        isEditable = arguments?.getBoolean(getString(R.string.editable)) ?: false
        drawing = arguments?.getParcelable(getString(R.string.drawing))!!
        marker = arguments?.getParcelable(getString(R.string.marker))!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMarkerBinding.inflate(inflater, container, true)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.marker = marker
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        markerTitle.isEditable = isEditable
        markerDescription.isEditable = isEditable
        addMarkerButton.visibility = when (isEditable) {
            true -> View.VISIBLE
            else -> View.GONE
        }
        addMarkerButton.setOnClickListener {
            if (markerTitle.isValid) {
                viewModel.postMarkers(drawing, marker)
                addMarkerButton.visibility = View.GONE
            }
        }
    }

}