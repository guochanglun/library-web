package com.gcl.library.repository;

import com.gcl.library.bean.UserBook;
import com.gcl.library.bean.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by gcl on 2016/12/16.
 * <p>
 * AdviceRepository
 */
public interface UserBookRepository extends JpaRepository<UserBook, Long> {
    List<UserBook> findByIsbn(String isbn);
}
