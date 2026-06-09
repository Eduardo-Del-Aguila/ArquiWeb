export interface TableAction<T> {
  label: string;
  icon: string;
  action: (row: T) => void;
}

export interface TableColumn {
  key: string;
  label: string;
}
