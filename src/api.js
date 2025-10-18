const API_BASE = process.env.REACT_APP_API_BASE || "http://localhost:8080/api";

export async function apiRequest(path, options = {}) {
  const token = localStorage.getItem("token");
  const headers = {
    "Content-Type": "application/json",
    ...(options.headers || {}),
  };
  if (token) headers["Authorization"] = `Bearer ${token}`;

  try {
    const response = await fetch(`${API_BASE}${path}`, {
      ...options,
      headers,
    });

    const text = await response.text();
    const data = text ? JSON.parse(text) : {};
    
    if (!response.ok) {
      const message = data?.message || data?.error || `HTTP ${response.status}: ${response.statusText}`;
      throw new Error(message);
    }
    
    return data;
  } catch (error) {
    if (error.name === 'TypeError' && error.message.includes('fetch')) {
      throw new Error('Network error: Unable to connect to server. Please check if the backend is running.');
    }
    throw error;
  }
}

export const AuthApi = {
  async login(username, password) {
    return apiRequest("/auth/login", {
      method: "POST",
      body: JSON.stringify({ username, password }),
    });
  },
};

export const BooksApi = {
  async issue(barcode) {
    return apiRequest("/books/issue", {
      method: "POST",
      body: JSON.stringify({ barcode }),
    });
  },
  async returnBook(barcode) {
    return apiRequest("/books/return", {
      method: "POST",
      body: JSON.stringify({ barcode }),
    });
  },
};

export const TransactionsApi = {
  async history() {
    return apiRequest("/transactions/history", { method: "GET" });
  },
};






