package ru.flymary.app.presentation.startwindow.catalogwindow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.flymary.app.databinding.NodeElementBinding
import ru.flymary.app.model.NodeDTO
import ru.flymary.app.remoteserver.RemoteServer

class NodeAdapter(
    private var onClick:(NodeDTO) -> Unit
):RecyclerView.Adapter<holder>() {

    private var nodes: List<NodeDTO> = listOf()

    fun setNodes(newNodes: List<NodeDTO>){
        nodes = newNodes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {
        return holder(NodeElementBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return nodes.size
    }

    override fun onBindViewHolder(holder: holder, position: Int) {
        val node = nodes[position]

        holder.binding.nodeName.text = node.name

        Glide.with(holder.binding.nodeImage).load("${RemoteServer.nodeApi}${node.id}/image").into(holder.binding.nodeImage)

        holder.binding.nodeImage.setOnClickListener {
            onClick(node)
        }
    }
}

class holder(val binding: NodeElementBinding):RecyclerView.ViewHolder(binding.root)