export default function Accordion() {
  return (
    <div>
      <Panel
        title="Coffee"
        content="コーヒーは、コーヒー豆から抽出される飲み物であり、世界中で広く愛されています。豆を挽いてお湯で淹れることで、香り高く濃厚な味わいが楽しめます。"
      />
      <Panel
        title="Tea"
        content="紅茶は、茶葉から抽出される飲み物であり、世界中で人気があります。茶葉をお湯で浸し、適切な時間で淹れることで、豊かな風味とアロマが楽しめます。"
      />
    </div>
  );
}

function Panel({ title, content }: { title: string; content: string }) {
  return (
    <section>
      <h2>{title}</h2>
      <button>hide</button>
      <p>{content}</p>
    </section>
  );
}
