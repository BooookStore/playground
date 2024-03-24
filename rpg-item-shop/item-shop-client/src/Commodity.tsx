export type Props = {
  name: string;
  description: string;
  price: number;
};

export default function Commodity({ name, description, price }: Props) {
  return (
    <div className="p-3 flex flex-col w-96 border-2 border-solid rounded-md shadow bg-slate-50 text-slate-700">
      <div>
        <p className="font-medium mb-2">{name}</p>
      </div>
      <div>
        <p className="text-slate-700 text-sm font-light mb-2">{description}</p>
      </div>
      <div>
        <p>
          <span className="mr-2">価格</span>
          {price}
          <span className="ml-1">Gold</span>
        </p>
      </div>
    </div>
  );
}
