package com.neticer.dip.util;

public class DCT2 {
	private static double a;
	public DCT2(double a){
		this.a = a;
	}
	public double[][] dctTrans(double[][] img, int iw, int ih, int bsize, int type) {
		int iter_num = 256 / bsize;
		double[][] dct_image = new double[iw][ih];
		double[][] dct_coef = new double[bsize][bsize];
		double[][] dct_coeft = new double[bsize][bsize];
		double[][] image = new double[bsize][bsize];

		coeff(dct_coef, bsize);

		for (int i = 0; i < bsize; i++) {
			for (int j = 0; j < bsize; j++) {
				dct_coeft[i][j] = dct_coef[j][i];
			}
		}

		if (type == 1) {
			for (int j = 0; j < iter_num; j++) {
				for (int i = 0; i < iter_num; i++) {
					for (int k = 0; k < bsize; k++) {
						for (int l = 0; l < bsize; l++) {
							image[k][l] = img[i * bsize + k][j * bsize + l];
						}
					}
					dct(image, dct_coeft, dct_coef, bsize);
					for (int k = 0; k < bsize; k++) {
						for (int l = 0; l < bsize; l++) {
							dct_image[i * bsize + k][j * bsize + l] = image[k][l];
						}
					}

				}
			}
		} else {
			for (int j = 0; j < iter_num; j++) {
				for (int i = 0; i < iter_num; i++) {
					for (int k = 0; k < bsize; k++) {
						for (int l = 0; l < bsize; l++) {
							image[k][l] = img[i * bsize + k][j * bsize + l];
						}
					}
					dct(image, dct_coef, dct_coeft, bsize);
					for (int k = 0; k < bsize; k++) {
						for (int l = 0; l < bsize; l++) {
							dct_image[i * bsize + k][j * bsize + l] = image[k][l];
						}
					}
				}
			}
		}
		return dct_image;
	}

	public void coeff(double[][] dct_coef, int n) {
		double sqrt_1 = 1.0 / Math.sqrt(2.0);
		for (int i = 0; i < n; i++) {
			dct_coef[0][i] = sqrt_1;
		}
		//Initialize coefficient 
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < n; j++) {
				dct_coef[i][j] = this.a* Math.cos(i * Math.PI * (j + 0.5) / ((double) n));
			}
		}
	}

	private void dct(double[][] a, double[][] b, double[][] c, int n) {
		double x;
		double[][] af = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				x = 0.0;
				for (int k = 0; k < n; k++) {
					x += a[i][k] * b[k][j];
				}
				af[i][j] = x;
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				x = 0.0;
				for (int k = 0; k < n; k++) {
					x += c[i][k] * af[k][j];
				}
				a[i][j] = 2.0 * x / ((double) n);
			}
		}
	}

}
