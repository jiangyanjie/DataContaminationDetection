package ca.bcit.comp2613.quiltpad.repository;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import ca.bcit.comp2613.quiltpad.model.Block;
import ca.bcit.comp2613.quiltpad.model.Piece;


public class CustomQueryHelper {
	private EntityManagerFactory emf;

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public CustomQueryHelper(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@SuppressWarnings("unchecked")
	public List<Piece> getPiecesOfBlockWithNativeQuery(Integer blockId) {
		List<Piece> retval = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Query query = em
					.createNativeQuery(
							" select "
									+ "       piece.* "
									+ "    from "
									+ "        block_piece "
									+ "    left join "
									+ "        piece "
									+ "            on block_piece.piece_id = piece.id where block_piece.block_id = :block_id",
							Piece.class);
			query.setParameter("block_id", blockId);
			retval = query.getResultList();
		} catch (Exception e) {

		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
		return retval;
	}
	
	
	public List<Piece> getPiecesOfBlock(Integer blockId) {
		List<Piece> retval = null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Block block = em.find(Block.class, blockId);
			block.getPieces().size(); // make database call
			return block.getPieces();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
		return retval;
	}
	
	
	public void addPieceToBlock(Integer blockId, Integer pieceId) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Block block = em.find(Block.class, blockId);
			Piece piece = em.find(Piece.class, pieceId);
			System.out.println(block.getPieces().size()); // make database call
			block.getPieces().add(piece);
			em.getTransaction().begin();
			em.persist(block);
			em.getTransaction().commit();
			System.out.println(block.getPieces().size()); // make database call
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}	
	}
	
	
	public void removePieceFromBlock(Integer blockId, Integer pieceId) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Block block = em.find(Block.class, blockId);
			block.getPieces().size(); // make database call
			Iterator<Piece> iter = block.getPieces().iterator();
			while (iter.hasNext()) {
				if (iter.next().getId().equals(pieceId)) {
					iter.remove();
				}
			}		
			em.getTransaction().begin();
			em.persist(block);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				em.close();
			} catch (Exception e) {
			}
		}
	}
	
}
