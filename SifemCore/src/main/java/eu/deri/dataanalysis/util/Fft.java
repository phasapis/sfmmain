package eu.deri.dataanalysis.util;

public class Fft {
	public static void complexToComplex(Integer sign, Integer n, float ar[], float ai[]) {
		float scale = (float) Math.sqrt(1.0f / n);

		Integer i, j;
		for (i = j = 0; i < n; ++i) {
			if (j >= i) {
				float tempr = ar[j] * scale;
				float tempi = ai[j] * scale;
				ar[j] = ar[i] * scale;
				ai[j] = ai[i] * scale;
				ar[i] = tempr;
				ai[i] = tempi;
			}
			Integer m = n / 2;
			while (m >= 1 && j >= m) {
				j -= m;
				m /= 2;
			}
			j += m;
		}

		Integer mmax, istep;
		for (mmax = 1, istep = 2 * mmax; mmax < n; mmax = istep, istep = 2 * mmax) {
			float delta = (float) sign * 3.141592654f / (float) mmax;
			for (Integer m = 0; m < mmax; ++m) {
				float w = (float) m * delta;
				float wr = (float) Math.cos(w);
				float wi = (float) Math.sin(w);
				for (i = m; i < n; i += istep) {
					j = i + mmax;
					float tr = wr * ar[j] - wi * ai[j];
					float ti = wr * ai[j] + wi * ar[j];
					ar[j] = ar[i] - tr;
					ai[j] = ai[i] - ti;
					ar[i] += tr;
					ai[i] += ti;
				}
			}
			mmax = istep;
		}
	}
}