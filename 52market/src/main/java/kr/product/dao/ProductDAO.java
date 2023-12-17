package kr.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.product.vo.Product_DetailVO;
import kr.product.vo.Product_FavVO;
import kr.product.vo.Product_MapVO;
import kr.product.vo.ProductVO;
import kr.util.DBUtil;

public class ProductDAO {
	private static ProductDAO instance = new ProductDAO();
	public static ProductDAO getInstance() {
		return instance;
	}
	private ProductDAO() {}

	//상품 등록
	public void insertProduct(ProductVO product, Product_DetailVO detail, Product_MapVO map)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		String sql = null;
		ResultSet rs = null;
		int num = 0;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			//1. sequence
			sql = "SELECT product_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
			
			//2. product_detail
			sql = "INSERT INTO product_detail (product_num,product_seller,product_category,product_price,product_image,product_name) "
					+ "VALUES (?,?,?,?,?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, num); // session user_num 작성시
			pstmt2.setInt(2, detail.getProduct_seller());
			pstmt2.setInt(3, detail.getProduct_category());
			pstmt2.setInt(4, detail.getProduct_price());
			pstmt2.setString(5, detail.getProduct_image());
			pstmt2.setString(6, detail.getProduct_name());
			pstmt2.executeUpdate();
			
			//3. product 
			sql = "INSERT INTO product (product_num,product_mem,product_title,product_image,product_content) "
					+ "VALUES (?,?,?,?,?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, num);
			pstmt3.setInt(2, product.getProduct_mem());
			pstmt3.setString(3, product.getProduct_title());
			pstmt3.setString(4, product.getProduct_image());
			pstmt3.setString(5, product.getProduct_content());
			pstmt3.executeUpdate();
			
			//4. Map
			sql = "INSERT INTO product_map (product_num,location_x,location_y,location,"
					+ "road_address_name,address_name) VALUES(?,?,?,?,?,?)";
			pstmt4 = conn.prepareStatement(sql);
			pstmt4.setInt(1, num);
			pstmt4.setString(2, map.getLocation_x());
			pstmt4.setString(3, map.getLocation_y());
			pstmt4.setString(4, map.getLocation());
			pstmt4.setString(5, map.getRoad_address_name());
			pstmt4.setString(6, map.getAddress_name());
			pstmt4.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}

	//관리자/사용자 - 전체 상품 개수/검색 상품 개수
		public int getProductCount(String keyfield,String keyword,int status)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String sub_sql = "";
			int count = 0;
			
			try {
				conn = DBUtil.getConnection();
				
				if(keyword!=null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql += "AND name LIKE ?";
					else if(keyfield.equals("2")) sub_sql += " AND title LIKE ?";
				}
				
				sql = "SELECT COUNT(*) FROM product WHERE status > ?" + sub_sql;
				pstmt = conn.prepareStatement(sql);
				
				if(keyword!=null && !"".equals(keyword)) {
					pstmt.setString(++count, "%"+keyword+"%");
				}
				rs = pstmt.executeQuery();
				if(rs.next()) {
					count = rs.getInt(1);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}		
			return count;
		}
		
		
		//관리자/사용자 - 전체 상품 개수/검색 상품 목록
		public List<Product_DetailVO> getListProduct(int start,int end,String keyfield,String keyword,int product_status)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<Product_DetailVO> list = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			
			try {
				conn = DBUtil.getConnection();
				
				if(keyword!=null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql += "AND name LIKE ?";
					else if(keyfield.equals("2")) sub_sql += " AND title LIKE ?";
				}
				
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM " +
					"(SELECT * FROM product INNER JOIN product_detail USING(product_num) WHERE product_status>=? "
					+ sub_sql + " ORDER BY product_reg_date DESC)a) WHERE rnum >= ? AND rnum <= ?";
							
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(++cnt, product_status);
				
				if(keyword!=null && !"".equals(keyword)) {
					pstmt.setString(++cnt, "%"+keyword+"%");
				}
				
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				
				rs = pstmt.executeQuery();
				
				list = new ArrayList<Product_DetailVO>();
				while(rs.next()) {
					Product_DetailVO detail = new Product_DetailVO();
					detail.setProduct_num(rs.getInt("product_num"));
					detail.setProduct_seller(rs.getInt("product_seller"));
					detail.setProduct_category(rs.getInt("product_category"));
					detail.setProduct_price(rs.getInt("product_price"));
					detail.setProduct_image(rs.getString("product_image"));
					detail.setProduct_name(rs.getString("product_name"));
					
					list.add(detail);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
	

	
	//상품 상세 (product)
	public ProductVO getProduct(int product_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO product = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM product p JOIN member m ON p.product_mem = m.mem_num "
					+ "LEFT OUTER JOIN member_detail USING(mem_num) WHERE product_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				product = new ProductVO();
				product.setProduct_num(rs.getInt("product_num"));
				product.setProduct_mem(rs.getInt("product_mem"));
				product.setMem_nickname(rs.getString("mem_nickname"));
				product.setMem_photo(rs.getString("mem_photo"));
				product.setProduct_content(rs.getString("product_content"));
				product.setProduct_image(rs.getString("product_image"));
				product.setProduct_hit(rs.getInt("product_hit"));
				product.setProduct_modify_date(rs.getDate("product_modify_date"));
				product.setProduct_reg_date(rs.getDate("product_reg_date"));
				product.setProduct_status(rs.getInt("product_status"));
				product.setProduct_title(rs.getString("product_title"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return product;
	}
	
	
	//상품 상세 (product_detail)
	public Product_DetailVO getProductDetail(int product_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product_DetailVO detail = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM product_detail p JOIN member m ON p.product_seller = m.mem_num "
					+ "LEFT OUTER JOIN member_detail USING(mem_num) WHERE product_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				detail = new Product_DetailVO();
				detail.setProduct_num(rs.getInt("product_num"));
				detail.setProduct_seller(rs.getInt("product_seller"));
				detail.setProduct_buyer(rs.getInt("product_buyer"));
				detail.setMem_nickname(rs.getString("mem_nickname"));
				detail.setMem_photo(rs.getString("mem_photo"));
				detail.setProduct_category(rs.getInt("product_category"));
				detail.setProduct_price(rs.getInt("product_price"));
				detail.setProduct_tradedate(rs.getDate("product_tradedate"));
				detail.setProduct_image(rs.getString("product_image"));
				detail.setProduct_name(rs.getString("product_name"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return detail;
	}
	
	//상품-지도 상세 (product_map)
	public Product_MapVO getProductMap(int product_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Product_MapVO map = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM product_map WHERE product_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				map = new Product_MapVO();
				map.setLocation_x(rs.getString("location_x"));
				map.setLocation_y(rs.getString("location_y"));
				map.setLocation(rs.getString("location"));
				map.setRoad_address_name(rs.getString("road_address_name"));
				map.setAddress_name(rs.getString("address_name"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return map;
	}
	
	
	//상품 수정
	public void updateProduct(ProductVO product, Product_DetailVO detail, Product_MapVO map, int product_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			//1. detail 수정
			sql = "UPDATE product_detail SET product_category=?,product_price=?,"
					+ "product_image=?,product_name=? WHERE product_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, detail.getProduct_category());
			pstmt.setInt(2, detail.getProduct_price());
			pstmt.setString(3, detail.getProduct_image());
			pstmt.setString(4, detail.getProduct_name());
			pstmt.setInt(5, product_num);
			pstmt.executeUpdate();
			
			//2. product 수정
			sql = "UPDATE product SET product_title=?,product_content=?,product_modify_date=SYSDATE,"
					+ "product_image=? WHERE product_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setString(1, product.getProduct_title());
			pstmt2.setString(2, product.getProduct_content());
			pstmt2.setString(3, product.getProduct_image());
			pstmt2.setInt(4, product_num);
			pstmt2.executeUpdate();
			
			//3. map 수정
			sql = "UPDATE product_map SET location_x=?,location_y=?,location=?,road_address_name=?,address_name=? WHERE product_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setString(1, map.getLocation_x());
			pstmt3.setString(2, map.getLocation_y());
			pstmt3.setString(3, map.getLocation());
			pstmt3.setString(4, map.getRoad_address_name());
			pstmt3.setString(5, map.getAddress_name());
			pstmt3.setInt(6, product_num);
			pstmt3.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt3, conn);
		}
	}
	

	//좋아요 개수
	public int selectFavCount(int product_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM product_fav WHERE product_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	
	//좋아요 선택 여부
	public Product_FavVO selectFav(Product_FavVO favVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product_FavVO fav = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM product_fav WHERE product_num=? AND mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, favVO.getProduct_num());
			pstmt.setInt(2, favVO.getMem_num());
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				fav = new Product_FavVO();
				fav.setProduct_num(rs.getInt("product_num"));
				fav.setMem_num(rs.getInt("mem_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return fav;
	}
	
	
	//좋아요 등록 (행 수로 셈)
	public void insertFav(Product_FavVO favVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try{
			conn = DBUtil.getConnection();
			sql = "INSERT INTO product_fav (product_num,mem_num) VALUES (?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, favVO.getProduct_num());
			pstmt.setInt(2, favVO.getMem_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	
	//좋아요 삭제
	public void deleteFav(Product_FavVO favVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM product_fav WHERE product_num=? AND mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, favVO.getProduct_num());
			pstmt.setInt(2, favVO.getMem_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	//상품 삭제
	public void deleteProduct(int product_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		ResultSet rs = null;
		String sql = null;
		int status = 0;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			//product_fav 삭제
			sql = "DELETE FROM product_fav WHERE product_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_num);
			pstmt.executeUpdate();
			
			//product_map
			sql = "DELETE FROM product_map WHERE product_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, product_num);
			pstmt2.executeUpdate();
			
			//product
			sql = "DELETE FROM product WHERE product_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, product_num);
			pstmt3.executeUpdate();
			
			//status 불러오기 (0일때 detail삭제)
			sql = "SELECT * FROM product WHERE product_num=?";
			pstmt4 = conn.prepareStatement(sql);
			pstmt4.setInt(1, product_num);
			rs = pstmt4.executeQuery();
			if(rs.next()) {
				status = rs.getInt("product_status");
			}
			
			//product_detail
			if(status==0) {
				sql = "DELETE FROM product_detail WHERE product_num=?";
				pstmt5 = conn.prepareStatement(sql);
				pstmt5.setInt(1, product_num);
				pstmt5.executeUpdate();
			}
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt5, null);
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
		
	
	//조회수 증가
	public void updateReadcount(int product_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE product SET product_hit=product_hit+1 WHERE product_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	
	
	//채팅
}
