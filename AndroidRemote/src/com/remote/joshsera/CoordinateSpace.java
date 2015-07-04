

package com.remote.joshsera;



public class CoordinateSpace {
	public Point3D x;
	public Point3D y;
	public Point3D z;
	
	
	public CoordinateSpace() {
		this.x = new Point3D();
		this.x.x = 0;
		this.x.y = 1;
		this.x.z = 0;
		this.y = new Point3D();
		this.y.x = 0;
		this.y.y = 0;
		this.y.z = 1;
		this.z = new Point3D();
		this.z.x = 1;
		this.z.y = 0;
		this.z.z = 0;
	}

	public CoordinateSpace(Point3D x, Point3D y, Point3D z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	public CoordinateSpace(float[] a, float[] m) {
		Point3D.copy(a, this.y = new Point3D());
		this.z = new Point3D();
		Point3D.cross(a, m, this.z);
		this.x = new Point3D();
		Point3D.cross(this.z, this.y, this.x);
		Point3D.normalize(this.x);
		Point3D.normalize(this.y);
		Point3D.normalize(this.z);
	}
	
	public void setSpace(float[] a, float[] m) {
		a[1] *= -1;
		m[1] *= -1;
		m[2] *= -1;

		Point3D.copy(a, this.z);
		Point3D.cross(a, m, this.y);
		Point3D.cross(this.z, this.y, this.x);
		Point3D.normalize(this.x);
		Point3D.normalize(this.y);
		Point3D.normalize(this.z);
	}
	
	public void setSpace(Point3D a, Point3D m) {
		a.y *= -1;
		m.y *= -1;
		m.z *= -1;
		Point3D.copy(a, this.z);
		Point3D.cross(a, m, this.y);
		Point3D.cross(this.z, this.y, this.x);
		Point3D.normalize(this.x);
		Point3D.normalize(this.y);
		Point3D.normalize(this.z);
	}
	
	public void copy(CoordinateSpace c) {
		Point3D.copy(c.x, this.x);
		Point3D.copy(c.y, this.y);
		Point3D.copy(c.z, this.z);
	}
}
