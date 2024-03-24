package Controller;

import java.util.List;

import Dao.RoomCategoryDao;
import Model.RoomCategory;

public class RoomCategoryController {
    private RoomCategoryDao roomCategoryDAO;

    public RoomCategoryController() {
        this.roomCategoryDAO = new RoomCategoryDao();
    }

    public void addRoomCategory(String categoryName) {
        roomCategoryDAO.addRoomCategory(categoryName);
    }

    public void updateRoomCategory(int categoryId, String categoryName) {
        roomCategoryDAO.updateRoomCategory(categoryId, categoryName);
    }

    public void deleteRoomCategory(int categoryId) {
        roomCategoryDAO.deleteRoomCategory(categoryId);
    }

    public List<RoomCategory> getAllRoomCategories() {
    	System.out.println("pppppppppppppppppppp");
        return roomCategoryDAO.getAllRoomCategories();
    }
}
