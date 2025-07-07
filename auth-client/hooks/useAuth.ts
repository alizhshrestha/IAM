import { jwtDecode } from "jwt-decode";
import { useEffect, useState } from "react";

type AuthenticatedUser = {
  username: string;
  roles: string[];
  tenantId: string;
  userId: string;
  email: string;
};

export function useAuth() {
  const [user, setUser] = useState<AuthenticatedUser | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = sessionStorage.getItem("access_token");
    if (!token) {
      setLoading(false);
      return;
    }

    try {
      const decoded: any = jwtDecode(token);
      const tenant = sessionStorage.getItem("tenant");

      setUser({
        username: decoded.sub,
        roles: decoded.roles || [],
        tenantId: tenant ?? decoded.tenant,
        userId: decoded.user_id ?? "",
        email: decoded.email ?? "",
      });
    } catch (err) {
      console.error("Invalid token", err);
    }

    setLoading(false);
  }, []);

  return { user, loading };
}
