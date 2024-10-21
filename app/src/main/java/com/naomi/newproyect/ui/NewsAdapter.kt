import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naomi.newproyect.R
import com.naomi.newproyect.domain.entities.RssItem

class NewsAdapter(private var newsItems: List<RssItem>, private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.item_title)
        val siteName: TextView = view.findViewById(R.id.item_site_name)
        val pubDate: TextView = view.findViewById(R.id.item_pub_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = newsItems[position]
        holder.title.text = item.title
        holder.siteName.text = item.siteName
        holder.pubDate.text = item.pubDate
        holder.itemView.setOnClickListener { onItemClick(item.link) }
    }

    override fun getItemCount() = newsItems.size

    fun updateData(newItems: List<RssItem>) {
        newsItems = newItems
        notifyDataSetChanged()
    }
}
