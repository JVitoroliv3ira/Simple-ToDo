const set = <C> (key: string, content: C): void => {
  localStorage.setItem(key, JSON.stringify(content));
}

const get = <C> (key: string): C | null => {
  const item = localStorage.getItem(key);
  if (item === null || item === undefined) {
    return null;
  }

  try {
    return JSON.parse(item);
  } catch(e) {
    return null;
  }
}

const remove = (key: string): void => {
  localStorage.removeItem(key);
}

export { set, get, remove };
