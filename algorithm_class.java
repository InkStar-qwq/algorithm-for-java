import java.util.*;

class algorithm{
	static long testnum[]={2,3,5,7,11,13,17,19};
	static int x,y;
	static long fastpow(long a,long b,long k){
	 	long ans=1,base=a;
		if(b==0)ans%=k;
		while(b>0){
			if(b%2==1){
				ans*=base;
				ans%=k;
			}
			base*=base;
			base%=k;
			b>>=1;
		}
		return ans;
	}
	static long fastpow(long a,long b){
	 	long ans=1,base=a;
		while(b>0){
			if(b%2==1){
				ans*=base;
			}
			base*=base;
			b>>=1;
		}
		return ans;
	}
	static void exgcd(int a,int b){
		if(b==0){
			x=1;y=0;
			return;
		}
		exgcd(b,a%b);
		int tmp=x;
		x=y;
		y=tmp-a/b*y;
		return;
	}//ans为x 排除负数(x%b+b)%b
	static int gcd(int a,int b){
		if(b>a)return gcd(b,a);
		if(b==0)return a;
		return gcd(b,a%b);
	}
	static boolean MRtest(long n)//判断素数
    {
        if (n==2) return 1;
        if((n&1)==0||n==1) return false;
        for (long i=0;i<8;i++) if(n==testnum[i])return true;
        long temp=n-1,t=0,nxt;
        while((temp&1)==0) temp>>=1,t++;
        for(long i=0;i<8;i++)
        {
            long a=testnum[i];
            long now=fastpow(a,temp,n);
            nxt=now;
            for(long j=1;j<=t;j++)
            {
                nxt=(now*now)%n;
                if(nxt==1&&now!=n-1&&now!=1) return false;
                now=nxt;
            }
            if(now!=1) return false;
        }
        return true;
	}
	public long fastpow(long base,long expo,long mode){
		long ret=1;
		while(expo>0){
			if(expo%2==1)ret=ret*base%mode;
			base=base*base%mode;
			expo/=2;
		}
		return ret;
	}
	public long fastpow(long base,long expo){
		long ret=1;
		while(expo>0){
			if(expo%2==1)ret=ret*base;
			base=base*base;
			expo/=2;
		}
		return ret;
	}
	public long exgcd(long a,long b){
		if(b==0){
			x=1;y=0;
			return a;
		}
		long ret=exgcd(b,a%b);
		long tmp=x;
		x=y;
		y=tmp-a/b*y;
		return ret;
	}
	public boolean combine(long[] a,long[] m,long tot){
		long d,c;
		for(int i=2;i<=tot;i++){
			d=exgcd(m[1],m[i]);
			c=a[i]-a[1];
			if(c%d!=0)return false;
			m[i]/=d;
			x=(c/d*x%m[i]+m[i])%m[i];
			a[1]=m[1]*x+a[1];
       	 	m[1]=m[1]*(m[i]/d);
        	a[1]%=m[1];
		}
		return true;
	}
	public long inverse(long a,long b){
		x=0;y=0;
		gcd(a,b);
		return (x%b+b)%b;
	}
	public long C(long n,long m,long mode){
		if(m>n)return 0;
		long ret=1,up,down;
		for(long 1;i<=m;i++){
			up=(n+1-i)%mode;
			down=inverse(i%mode,mode);
			ret=ret*up%mode*down%mode;
		}
		return ret;
	}
	public long C1(long n,long m,long mode){
		if(m==0) return 1;
   		return C(n%mode,m%mode,mode)*C1(n/mode,m/mode,mode)%mode;
	}
	public long cal(long n,long p,long mode){
		if(n==0)return 1;
		long m=n/mode,tmp=1;
    	for (long i=1;i<=mode;i++)if(i%p)tmp=tmp*i%mode;
    	long ret=fastpow(tmp,m,mode);
    	for (long i=m*mode+1;i<=n;i++)if(i%p)ret=ret*i%mode;
    	return ret*cal(n/p,p,mode)%mode;
	}
	public long C2(long n,long m,long p,long mode){
		long a,b,c,cnt=0,tmp;
		for(tmp=n;tmp;tmp/=p)cnt+=tmp/p;
    	for(tmp=m;tmp;tmp/=p)cnt-=tmp/p;
    	for(tmp=n-m;tmp;tmp/=p)cnt-=tmp/p;
    	long ret=fastpow(p,cnt,mode);
    	a=cal(n,p,mode);
    	b=cal(m,p,mode);
    	c=cal(n-m,p,mode);
    	ret=ret*a%mode*inverse(b,mode)%mode*inverse(c,mode)%mode;
    	return ret;
	}
	//C(n,m)%mode
	public long exLucas(long n,long m,long mode){
		long cnt,tot=0;
		long [] A = new long [205];
		long [] M = new long [205];
		for(long i=2;i*i<=mode;i++)
        if(mode%i==0){
			for (cnt=0;mode%i==0;cnt++)mode/=i;
			M[++tot]=fastpow(i,cnt);
			if(cnt==1) A[tot]=C1(n,m,i);
			else A[tot]=C2(n,m,i,M[tot]);
        }
		if(mode>1){
			M[++tot]=mode;
			A[tot]=C1(n,m,mode);
    	}
    	combine(A,M,tot);
    	return A[1];
		}
}