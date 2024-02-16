
type InputFieldProps = {
  label: string;
  name: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  type?: string;
};

const InputField = ({ label, name, value, onChange, type = 'text' }: InputFieldProps) => (
  <div className="mb-8">
    <label className="block mb-2 text-sm font-medium text-gray-950">{label}</label>
    <input
      name={name}
      type={type}
      value={value}
      onChange={onChange}
      className="w-full p-3 rounded text-sm"
      placeholder={`Informe o seu ${label.toLowerCase()}`}
    />
  </div>
);
export default InputField;
