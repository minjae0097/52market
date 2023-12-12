package kr.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.product.vo.ProductDetailVO;
import kr.product.vo.ProductVO;
import kr.util.DBUtil;

public class ProductDAO {
	private static ProductDAO instance = new ProductDAO();
	public static ProductDAO getInstance() {
		return instance;
	}
	private ProductDAO() {}

	//상품 등록
	public void insertProduct(ProductVO product, ProductDetailVO detail)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
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
			
			//2. product_detail저장
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
			
			//3. product 저장
			sql = "INSERT INTO product (product_num,product_title,product_content) "
					+ "VALUES (?,?,?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, num);
			pstmt3.setString(2, product.getProduct_title());
			pstmt3.setString(3, product.getProduct_content());
			pstmt3.executeUpdate();
			
			conn.commit();
			
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}

	//상품 리스트
	public List<ProductDetailVO> getListProduct(int start, int end, String keyfield, String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProductDetailVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			//검색 처리 추후 추가
			if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "AND name LIKE ?";
				else if(keyfield.equals("2")) sub_sql += " AND title LIKE ?";
			}
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM product p LEFT OUTER JOIN "
					+ "product_detail d ON p.product_num=d.product_num " + sub_sql
					+ " ORDER BY p.product DESC NULLS LAST)a) "
					+ "WHERE rnum>=? AND rnum<=?";
			
			pstmt = conn.prepareStatement(sql);
			
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<ProductDetailVO>();
			while(rs.next()) {
				ProductDetailVO detail = new ProductDetailVO();
				detail.setProduct_buyer(rs.getInt("product_buyer"));
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
	
	//상품 수정
	//상품 삭제
	
	//전체,검색 레코드 수
	//전체,검색 글 목록
	
	//조회수
	
	//좋아요
	
	//채팅
}
