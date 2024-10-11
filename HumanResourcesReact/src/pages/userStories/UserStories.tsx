import React, { useEffect, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import NavBar from '../../components/molecules/NavBar';
import { AppDispatch, RootState } from '../../store';
import { fetchGetCommentList } from '../../store/future/commentSlice';
import UserStoryCard from '../../components/molecules/Storycard';
import Grid from '@mui/material/Grid';

function UserStories() {
  const dispatch = useDispatch<AppDispatch>();
  const { comments, loading, error } = useSelector((state: RootState) => state.comment);
  const isFetching = useRef(false);

  useEffect(() => {
    if (!isFetching.current && !loading) {
      isFetching.current = true;
      dispatch(fetchGetCommentList({ page: 1, size: 10 }))
        .then(() => isFetching.current = false)
        .catch(() => isFetching.current = false);
    }
  }, [dispatch, loading]);

  return (
    <>
      <div className="row">
        <NavBar />
      </div>

      <div className="row m-5">
        {loading && <p>Loading...</p>}
        {error && <p>Error: {error}</p>}
        {comments.length === 0 && !loading && <p>No comments available</p>}
        <div className="col-12 mt-5">
          <Grid container spacing={3}>
            {comments.map((comment, index) => (
              <UserStoryCard
                key={index}
                companyName={comment.companyName}
                managerName={comment.companyManagerFirstName}
                title="Personel Müdürü"
                shortDescription={comment.content}
                photo={comment.companyManagerAvatar || 'https://via.placeholder.com/150'} // Varsayılan resim
              />
            ))}
          </Grid>
        </div>
      </div>
    </>
  );
}

export default UserStories;
