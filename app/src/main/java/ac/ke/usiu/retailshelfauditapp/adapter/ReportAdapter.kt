package ac.ke.usiu.retailshelfauditapp.adapter

import ac.ke.usiu.retailshelfauditapp.model.Report
import ac.ke.usiu.retailshelfauditapp.databinding.ItemReportBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ReportAdapter(private val reports: ArrayList<Report>) :
    RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    class ReportViewHolder(val binding: ItemReportBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ItemReportBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val report = reports[position]

        holder.binding.txtReportId.text = "Report ${report.reportId}"
        holder.binding.txtDate.text = report.date
        holder.binding.txtProductCounts.text =
            "Coca-Cola: ${report.cocaColaCount}   Fanta: ${report.fantaCount}   Sprite: ${report.spriteCount}"
        holder.binding.txtEmptySpaces.text = "Empty Spaces: ${report.emptySpaces}"
    }

    override fun getItemCount(): Int {
        return reports.size
    }
}