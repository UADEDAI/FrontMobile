package com.uade.daitp.owner.home.presentation.adapters

//class CinemaRoomAdapter(
//    private val cinemas: List<Cinema>,
//    private val viewModel: OwnerCinemaViewModel
//) :
//    RecyclerView.Adapter<CinemaRoomAdapter.ViewHolder>() {
//
//    class ViewHolder(
//        private val binding: ListItemCinemaBinding,
//        private val viewModel: OwnerHomeViewModel
//    ) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(cinema: Cinema) {
//            binding.itemCinemaTitle.text = cinema.name
//            binding.itemCinemaMovies.text = "5 Movies"
//            binding.itemCinemaRoom.text = "5 Rooms"
//            //TODO
//
//            binding.itemCinemaDeleteButton.setOnClickListenerWithThrottle {
//                MaterialAlertDialogBuilder(itemView.context)
//                    .setMessage(itemView.resources.getString(R.string.cinema_dialog_text))
//                    .setNegativeButton(itemView.resources.getString(R.string.decline)) { _, _ -> }
//                    .setPositiveButton(itemView.resources.getString(R.string.accept)) { _, _ ->
//                        viewModel.delete(cinema)
//                    }
//                    .show()
//            }
//
//            binding.itemCinemaEditButton.setOnClickListenerWithThrottle {
//                val bundle = Bundle()
//                bundle.putInt(OwnerCinemaFormFragment.CINEMA_ID, cinema.id)
//                itemView.findNavController()
//                    .navigate(R.id.action_ownerHomeFragment_to_ownerCinemaFormFragment, bundle)
//            }
//
//            binding.root.setOnClickListenerWithThrottle {
//                val bundle = Bundle()
//                bundle.putInt(OwnerCinemaFragment.CINEMA_ID, cinema.id)
//                itemView.findNavController()
//                    .navigate(R.id.action_ownerHomeFragment_to_ownerCinemaFragment, bundle)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding =
//            ListItemCinemaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding, viewModel)
//    }
//
//    override fun getItemCount() = cinemas.size
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(cinemas[position])
//    }
//
//}