import java.util.ArrayList;
import java.util.List;

public class MenuRecursion {
	//子节点
	static List<Menu> fatherMenu = new ArrayList<Menu>();
	//子节点
	static List<Menu> childMenu = new ArrayList<Menu>();

	/**
	 * 获取某个父节点下面的所有子节点
	 *
	 * @param menuList
	 * @param pid
	 * @return
	 */
	public static List<Menu> treeMenuList(List<Menu> menuList, int pid) {
		for (Menu mu : menuList) {
			//遍历出父id等于参数的id，add进子节点集合
			if (Integer.valueOf(mu.getPid()) == pid) {
				//递归遍历下一级
				treeMenuList(menuList, Integer.valueOf(mu.getId()));
				childMenu.add(mu);
			}
		}
		return childMenu;
	}


	public static void main(String args[]) {
		List<Menu> menuList = new ArrayList<Menu>();
		Menu mu = new Menu();
		mu.setId("1");
		mu.setName("系统");
		mu.setPid("0");
		Menu mu1 = new Menu();
		mu1.setId("2");
		mu1.setName("用户");
		mu1.setPid("1");
		Menu mu2 = new Menu();
		mu2.setId("3");
		mu2.setName("角色");
		mu2.setPid("1");
		Menu mu3 = new Menu();
		mu3.setId("4");
		mu3.setName("权限");
		mu3.setPid("1");


		Menu mu4 = new Menu();
		mu4.setId("5");
		mu4.setName("枪支列表");
		mu4.setPid("2");
		Menu mu5 = new Menu();
		mu5.setId("6");
		mu5.setName("警告信息");
		mu5.setPid("2");
		Menu mu6 = new Menu();
		mu6.setId("7");
		mu6.setName("人员信息");
		mu6.setPid("0");
		Menu mu7 = new Menu();
		mu7.setId("8");
		mu7.setName("枪支轨迹");
		mu7.setPid("0");

		menuList.add(mu);
		menuList.add(mu1);
		menuList.add(mu2);
		menuList.add(mu3);
		menuList.add(mu4);
		menuList.add(mu5);
		menuList.add(mu6);
		menuList.add(mu7);

		List<Menu> childList = treeMenuList(menuList, 1);
		System.out.println(childList);
		/*for (Menu m : childList) {
			System.out.println(m.getId() + "   " + m.getName());
		}*/
	}
}